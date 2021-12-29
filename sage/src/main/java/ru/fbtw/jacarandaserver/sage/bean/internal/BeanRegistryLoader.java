package ru.fbtw.jacarandaserver.sage.bean.internal;


import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import ru.fbtw.jacarandaserver.sage.bean.internal.dependency.BeanDependency;
import ru.fbtw.jacarandaserver.sage.bean.internal.dependency.BeanInjector;
import ru.fbtw.jacarandaserver.sage.bean.internal.dependency.ListBeanDependency;
import ru.fbtw.jacarandaserver.sage.bean.internal.dependency.SingleBeanDependency;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.BeanCreationException;
import ru.fbtw.jacarandaserver.sage.bean.internal.exception.CycleBeanException;
import ru.fbtw.util.ListUtils;
import ru.fbtw.util.Pair;
import ru.fbtw.util.reflect.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BeanRegistryLoader {

	public static final String SAGE_BEEN_ANNOTATION_PACKAGE = "ru.fbtw.jacarandaserver.sage.bean.annotation";
	public static final String DEFAULT_TARGET_PACKAGE = ".";
	private final String targetPackage;


	public BeanRegistryLoader() {
		this(DEFAULT_TARGET_PACKAGE);
	}

	public BeanRegistryLoader(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public BeanRegistry loadBeans() {
		List<Class<? extends Annotation>> beanDeclarationAnnotations = getBeanDeclarationAnnotations();
		Stream<BeanTemplate> beanTemplateStream = getBeanTemplateStream(beanDeclarationAnnotations);

		Map<Class<?>, List<BeanTemplate>> dependencyGraph = buildDependencyGraph(beanTemplateStream);

		return BeanRegistry.buildBeanRegistry(dependencyGraph);
	}

	private Map<Class<?>, List<BeanTemplate>> buildDependencyGraph(Stream<BeanTemplate> beanTemplateStream) {
		Map<Class<?>, List<BeanTemplate>> dependencyGraph = initDependencyGraph(beanTemplateStream);

		dependencyGraph.get(Object.class)
				.stream()
				.filter(beanTemplate -> !beanTemplate.hasInjector())
				.forEach(beanTemplate -> buildInjector(dependencyGraph, beanTemplate));

		dependencyGraph.get(Object.class)
				.forEach(beanTemplate -> checkCycleBeans(beanTemplate, new HashSet<>()));

		dependencyGraph.get(Object.class)
				.stream()
				.filter(BeanTemplate::isNotInitialized)
				.forEach(BeanTemplate::getBean);

		return dependencyGraph;
	}

	private void checkCycleBeans(BeanTemplate node, Set<BeanTemplate> refs) {
		if (refs.contains(node)) {
			throw new CycleBeanException();
		}
		refs.add(node);
		for (BeanTemplate dependency : node.getDependencies()) {
			checkCycleBeans(dependency, refs);
		}
		refs.remove(node);
	}

	private void buildInjector(Map<Class<?>, List<BeanTemplate>> dependencyGraph, BeanTemplate beanTemplate) {
		Constructor<?> constructor = getBeanConstructor(beanTemplate);

		Type[] genericParameterTypes = constructor.getGenericParameterTypes();
		BeanInjector.BeanInjectorBuilder injectorBuilder = BeanInjector.builder();

		for (Type param : genericParameterTypes) {
			BeanDependency dependency;
			if (param instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType) param;
				dependency = ClassUtils.isList(param)
						? getListBeanDependency(dependencyGraph, beanTemplate, type)
						: getSingleBeanDependency(dependencyGraph, beanTemplate, (Class<?>) type.getRawType());
			} else {
				dependency = getSingleBeanDependency(dependencyGraph, beanTemplate, (Class<?>) param);
			}


			injectorBuilder.addDependency(dependency);
		}

		beanTemplate.setInjector(injectorBuilder.build());
	}

	private void checkDependencyExistence(BeanTemplate beanTemplate, List<BeanTemplate> beanTemplatesGroupByClass) {
		if (beanTemplatesGroupByClass == null || beanTemplatesGroupByClass.isEmpty()) {
			throw new BeanCreationException(beanTemplate.getClazz(), null);
		}
	}

	private BeanDependency getListBeanDependency(
			Map<Class<?>, List<BeanTemplate>> dependencyGraph,
			BeanTemplate beanTemplate,
			ParameterizedType param
	) {
		Class<?> actualType = (Class<?>) (param.getActualTypeArguments()[0]);
		List<BeanTemplate> beanTemplatesGroupByClass = dependencyGraph.get(actualType);
		checkDependencyExistence(beanTemplate, beanTemplatesGroupByClass);

		return new ListBeanDependency(beanTemplatesGroupByClass);
	}

	private BeanDependency getSingleBeanDependency(
			Map<Class<?>, List<BeanTemplate>> dependencyGraph,
			BeanTemplate beanTemplate,
			Class<?> param
	) {
		List<BeanTemplate> beanTemplatesGroupByClass = dependencyGraph.get(param);
		checkDependencyExistence(beanTemplate, beanTemplatesGroupByClass);
		BeanTemplate dependencyTemplate = beanTemplatesGroupByClass.get(0);

		return new SingleBeanDependency(dependencyTemplate);
	}

	private Constructor<?> getBeanConstructor(BeanTemplate beanTemplate) {
		Constructor<?>[] constructors = beanTemplate.getClazz()
				.getConstructors();
		if (constructors.length != 1) {
			throw new BeanCreationException(beanTemplate.getClazz(), null);
		}

		return constructors[0];
	}

	private Map<Class<?>, List<BeanTemplate>> initDependencyGraph(Stream<BeanTemplate> beanTemplateStream) {
		return beanTemplateStream
				.flatMap(BeanRegistryLoader::expandTypes)
				.collect(Collectors.toMap(
						Pair<Class<?>, BeanTemplate>::getKey,
						value -> Collections.singletonList(value.getValue()),
						ListUtils::mergeLists
				));
	}

	private Stream<BeanTemplate> getBeanTemplateStream(List<Class<? extends Annotation>> beanDeclarationAnnotations) {
		Configuration beanScannerConfig = new ConfigurationBuilder().forPackages(targetPackage);
		Reflections beanScanner = new Reflections(beanScannerConfig);
		Stream<BeanTemplate> beanTemplateStream = Stream.empty();

		for (Class<? extends Annotation> beanDeclarationAnnotation : beanDeclarationAnnotations) {
			BeenComponent componentConfiguration = beanDeclarationAnnotation
					.getDeclaredAnnotation(BeenComponent.class);

			if (componentConfiguration != null && componentConfiguration.source() == ElementType.TYPE) {
				Stream<BeanTemplate> beansStream = beanScanner
						.getTypesAnnotatedWith(beanDeclarationAnnotation)
						.stream()
						.map(bean -> new BeanTemplate(bean, beanDeclarationAnnotation));
				beanTemplateStream = Stream.concat(beanTemplateStream, beansStream);
			}
		}
		return beanTemplateStream;
	}

	private List<Class<? extends Annotation>> getBeanDeclarationAnnotations() {
		Configuration beanAnnotationScannerConfig = new ConfigurationBuilder()
				.forPackages(SAGE_BEEN_ANNOTATION_PACKAGE);
		Reflections beanAnnotationScanner = new Reflections(beanAnnotationScannerConfig);

		@SuppressWarnings("unchecked")
		List<Class<? extends Annotation>> beanDeclarationAnnotations =
				beanAnnotationScanner.getTypesAnnotatedWith(BeenComponent.class)
						.stream()
						.map(clazz -> (Class<? extends Annotation>) clazz)
						.collect(Collectors.toList());

		return beanDeclarationAnnotations;
	}


	private static Stream<? extends Pair<Class<?>, BeanTemplate>> expandTypes(BeanTemplate beanTemplate) {
		Class<?> beanClazz = beanTemplate.getClazz();
		List<Pair<Class<?>, BeanTemplate>> beanAliases = new ArrayList<>();
		ClassUtils.expandTypes(beanClazz, clazz -> beanAliases.add(new Pair<>(clazz, beanTemplate)));
		return beanAliases.stream();
	}
}
