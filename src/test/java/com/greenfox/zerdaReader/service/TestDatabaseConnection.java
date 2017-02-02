package com.greenfox.zerdaReader.service;

/**
 * Created by Rita on 2017-02-01.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@Configuration
//@ContextConfiguration(classes = ZerdaReaderApplication.class)
//@NoArgsConstructor
//public class TestDatabaseConnection {
//
//    private void handleSetUpOperation() throws Exception {
//        final IDatabaseConnection conn = getConnection();
//        final IDataSet data = getDataSet();
//        try {
//            DatabaseOperation.CLEAN_INSERT.execute(conn, data);
//        } finally {
//            conn.close();
//        }
//    }
//
//    private IDataSet getDataSet() throws IOException,
//            DataSetException {
//        return new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Green Fox Academy\\greenfox\\Zerda-reader-Olivin\\src\\test\\resources\\dataset.xml"));
//    }
//
//    private IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
//        Class.forName("org.hsqldb.jdbcDriver");
//        return new DatabaseConnection(DriverManager.getConnection("jdbc:hsqldb:sample", "sa", ""));
//    }

//    @Bean
//    public TestDatabaseConnection getTestDbConnectionBean() {
//        return new TestDatabaseConnection();
//    }

//    public TestDatabaseConnection() {
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver");
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:sample");
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
//        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
//    }
//
//    @Bean


//    protected IDataSet getDataSet() throws Exception {
//        return new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Green Fox Academy\\greenfox\\Zerda-reader-Olivin\\src\\test\\resources\\dataset.xml"));
//    }
//
//    protected DatabaseOperation getSetUpOperation() throws Exception {
//        return DatabaseOperation.REFRESH;
//    }
//
//    protected DatabaseOperation getTearDownOperation() throws Exception {
//        return DatabaseOperation.NONE;
//    }
//
//    public void valami() throws Exception {
//        // Fetch database data after executing your code
//        IDataSet databaseDataSet = getConnection().createDataSet();
//        ITable actualTable = databaseDataSet.getTable("feeds");
//
//        // Load expected data from an XML dataset
//        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("D:\\Green Fox Academy\\greenfox\\Zerda-reader-Olivin\\src\\test\\resources\\expectedDatasetTest1.xml"));
//        ITable expectedTable = expectedDataSet.getTable("feeds");
//    }
//}
