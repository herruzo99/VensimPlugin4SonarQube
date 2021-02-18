package es.uva.locomotion.service;

import es.uva.locomotion.model.Category;
import es.uva.locomotion.model.CategoryMap;
import es.uva.locomotion.testutilities.ServiceTestUtilities;
import es.uva.locomotion.utilities.exceptions.ConnectionFailedException;
import es.uva.locomotion.utilities.logs.LoggingLevel;
import es.uva.locomotion.utilities.logs.VensimLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class TestServiceControllerCategories {

    @After
    public void resetDbFacade() {
        DBFacade.handler = new ServiceConnectionHandler();
    }

    public ServiceController getAuthenticatedServiceController(String dictionaryService) {
        ServiceController controller = spy(new ServiceController(dictionaryService));
        doReturn(true).when(controller).isAuthenticated();
        doNothing().when(controller).authenticate(anyString(), anyString());
        return controller;

    }

    
    @Test
    public void testGetCategoriesControllerMakesCorrectCall() {
        ServiceConnectionHandler mockHandler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[]");
        DBFacade.handler = mockHandler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");

        controller.getCategoriesFromDb();

        verify(mockHandler, Mockito.times(1)).sendCategoriesRequestToDictionaryService(any(), any());

    }

    @Test
    public void testGetCategoriesDictionaryInvalidServiceUrlMissingProtocol() {
        ServiceController controller = getAuthenticatedServiceController("www.falseservice.com");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetCategoriesDictionaryInvalidServiceUrlInvalidFormat() {
        ServiceController controller = getAuthenticatedServiceController("http://\\$*^");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();


        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetCategoriesDictionaryInvalidServiceUrlInvalidProtocol() {
        ServiceController controller = getAuthenticatedServiceController("smtp://address:password@coolmail.com");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetCategoriesDictionaryMissingServiceEmptyUrl() {
        ServiceController controller = getAuthenticatedServiceController("");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).unique("Missing dictionary service parameter.\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.INFO);
    }

    @Test
    public void testGetCategoriesDictionaryMissingServiceNullUrl() {
        ServiceController controller = getAuthenticatedServiceController(null);
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).unique("Missing dictionary service parameter.\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.INFO);
    }

    @Test
    public void testGetCategoriesDictionaryConnectionFailed() {
        ServiceConnectionHandler handler = mock(ServiceConnectionHandler.class);
        when(handler.sendCategoriesRequestToDictionaryService(any(), any())).thenThrow(new ConnectionFailedException(null));
        DBFacade.handler = handler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).unique("The dictionary service was unreachable.\n" +
                "Injection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);

    }

    @Test
    public void testGetCategoriesDictionaryInvalidFormatNotAList() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{'name':'Juan'}");
        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();
        Assert.assertNull(actualValue);
        verify(logger).error("The response of the dictionary service wasn't valid. Expected an array. Dictionary response: {'name':'Juan'}.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "Injection of new categories can't be done without the categories from the dictionary.");

    }


    @Test
    public void testGetCategoriesDictionaryInvalidFormatNotAnCorrectList() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[{\"category\":\"foo\"}]");

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap actualValue = controller.getCategoriesFromDb();

        Assert.assertNull(actualValue);
        verify(logger).error("The response of the dictionary service wasn't valid. Missing 'name' field from a category. Dictionary response: [{\"category\":\"foo\"}].\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "Injection of new categories can't be done without the categories from the dictionary.");
    }


    @Test
    public void testGetCategoriesConsecutiveDifferentErrorsAreLogged() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{'name':'Juan'}");

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        controller.getCategoriesFromDb();
        controller.getCategoriesFromDb();
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[{\"category\":\"foo\"}]");
        controller.getCategoriesFromDb();
        controller.getCategoriesFromDb();


        verify(logger,atLeastOnce()).error("The response of the dictionary service wasn't valid. Expected an array. Dictionary response: {'name':'Juan'}.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "Injection of new categories can't be done without the categories from the dictionary.");

        verify(logger,atLeastOnce()).error("The response of the dictionary service wasn't valid. Missing 'name' field from a category. Dictionary response: [{\"category\":\"foo\"}].\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "Injection of new categories can't be done without the categories from the dictionary.");
    }


    @Test
    public void testInjectNewCategoriesNullDbCategoryList() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;


        CategoryMap list = new CategoryMap();

        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewCategories(list.getCategoriesAndSubcategories(), null);


        verify(logger, never()).info(anyString());
        verify(DBFacade.handler, never()).injectCategories(any(), any(), any());
    }


    @Test
    public void testInjectNewCategoriesOnlyIncludesNewAndValidCategories() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap foundList = new CategoryMap();

        Category c1 = new Category("Category_1");
        Category sc1 = new Category("Subcategory_1");
        Category sc2 = new Category("Subcategory_with_bad_name_2$");
        c1.addSubcategory(sc1);
        c1.addSubcategory(sc2);
        foundList.add(c1);
        Category c2 = new Category("Category_empty_2");
        foundList.add(c2);

        Category c3 = new Category("Category_with_bad_name 3");
        Category sc3 = new Category("Subcategory_3_super_with_bad_name");
        c3.addSubcategory(sc3);
        foundList.add(c3);
        Category c4 = new Category("Category_4");
        Category sc4 = new Category("Subcategory_4");
        c4.addSubcategory(sc4);
        foundList.add(c4);
        Category c5 = new Category("Category_already_in_db_5");
        Category sc5 = new Category("Subcategory_already_in_db_5");
        Category sc6 = new Category("Subcategory_6");
        c5.addSubcategory(sc5);
        c5.addSubcategory(sc6);
        foundList.add(c5);


        CategoryMap dbList = new CategoryMap();
        Category dbc5 = new Category("Category_already_in_db_5");
        Category dbsc5 = new Category("Subcategory_already_in_db_5");
        dbc5.addSubcategory(dbsc5);
        dbList.add(dbc5);
        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), dbList.getCategoriesAndSubcategories());


        verify(logger, times(1)).info("Injected categories: [Category_1, Category_4, Category_empty_2, Subcategory_1, Subcategory_4, Subcategory_6]");
    }
    @Test
    public void testInjectNewCategoriesNoneInjected() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap foundList = new CategoryMap();

        Category c1 = new Category("Category_1");
        Category sc1 = new Category("Subcategory_1");
        Category sc2 = new Category("Subcategory_with_bad_name_2$");
        c1.addSubcategory(sc1);
        c1.addSubcategory(sc2);
        foundList.add(c1);


        CategoryMap dbList = new CategoryMap();

        Category dbc1 = new Category("Category_1");
        Category dbsc1 = new Category("Subcategory_1");
        dbc1.addSubcategory(dbsc1);
        dbList.add(dbc1);

        ServiceController controller = getAuthenticatedServiceController("https://localhost");
        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), dbList.getCategoriesAndSubcategories());

        verify(logger, never()).info(any());
    }
    @Test
    public void testInjectNewCategoriesEmptyService() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        ServiceController controller = getAuthenticatedServiceController("");

        CategoryMap foundList = new CategoryMap();
        Category c1 = new Category("Category_1");
        foundList.add(c1);

        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), new ArrayList<>());


        verify(logger, times(1)).unique("Missing dictionary service parameter.\nInjection of new categories can't be done without the categories from the dictionary."
                , LoggingLevel.INFO);
    }

    @Test
    public void testInjectNewCategoriesNullService() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        ServiceController controller = getAuthenticatedServiceController(null);

        CategoryMap foundList = new CategoryMap();
        Category c1 = new Category("Category_1");
        foundList.add(c1);
        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), new ArrayList<>());


        verify(logger, times(1)).unique("Missing dictionary service parameter.\nInjection of new categories can't be done without the categories from the dictionary."
                , LoggingLevel.INFO);
    }

    @Test
    public void testInjectNewCategoriesConnectionFailed() {
        ServiceConnectionHandler handler = mock(ServiceConnectionHandler.class);
        when(handler.injectCategories(any(), any(), any())).thenThrow(new ConnectionFailedException(null));
        DBFacade.handler = handler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        CategoryMap foundList = new CategoryMap();
        Category c1 = new Category("Category_1");
        foundList.add(c1);

        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), new ArrayList<>());


        verify(logger, times(1)).unique("The dictionary service was unreachable.\nInjection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);
    }


    @Test
    public void testInjectNewCategoriesMissingServiceProtocol() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.LOG = logger;

        ServiceController controller = getAuthenticatedServiceController("www.google.com");

        CategoryMap foundList = new CategoryMap();
        Category c1 = new Category("Category_1");
        foundList.add(c1);

        controller.injectNewCategories(foundList.getCategoriesAndSubcategories(), new ArrayList<>());


        verify(logger, times(1))
                .unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\nInjection of new categories can't be done without the categories from the dictionary.", LoggingLevel.ERROR);
    }


}

