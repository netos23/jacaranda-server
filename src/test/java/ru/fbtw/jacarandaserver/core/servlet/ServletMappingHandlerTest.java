package ru.fbtw.jacarandaserver.core.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.fbtw.util.pathtree.PathTree;

class ServletMappingHandlerTest {

	@Test
	public void testMap(){
		PathTree<String> servletPath = new PathTree<>();

		String rootLevel = "root";
		String level1 = "level1-0";

		servletPath.put("/", rootLevel);
		servletPath.put("/test/", level1);
		servletPath.put("/test/test1/","level2-0");
		servletPath.put("/test/test1/test2/","level3-0");
		servletPath.put("/test/test1/test2/test4","level4-0");


		Assertions.assertEquals(rootLevel, servletPath.get("/name/"));
		Assertions.assertEquals(level1, servletPath.get("/test/test2/"));
	}

}