package de.fhg.iais.roberta.javaServer.basics;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.server.Server;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import de.fhg.iais.roberta.javaServer.restServices.all.ClientProgram;
import de.fhg.iais.roberta.javaServer.restServices.all.ClientUser;
import de.fhg.iais.roberta.main.ServerStarter;
import de.fhg.iais.roberta.persistence.util.DbSetup;
import de.fhg.iais.roberta.persistence.util.HttpSessionState;
import de.fhg.iais.roberta.persistence.util.SessionFactoryWrapper;
import de.fhg.iais.roberta.robotCommunication.ev3.Ev3Communicator;
import de.fhg.iais.roberta.robotCommunication.ev3.Ev3CompilerWorkflow;
import de.fhg.iais.roberta.testutil.Helper;
import de.fhg.iais.roberta.testutil.JSONUtilForServer;
import de.fhg.iais.roberta.testutil.SeleniumHelper;
import de.fhg.iais.roberta.util.Util;
import de.fhg.iais.roberta.util.testsetup.IntegrationTest;

@Category(IntegrationTest.class)
public class RoundTripTest {
    private static final String resourcePath = "/roundtrip/";
    private static final String[] blocklyPrograms = {
        "move",
        "drive",
        "show",
        "sound",
        "status_light",
        "sensors",
        "logic",
        "control_stmt",
        "wait",
        "math",
        "text",
        "list",
        "methods",
        "bluetooth"
    };

    private static WebDriver driver;
    private static String baseUrl;
    private static boolean browserVisibility;
    private static Server server;

    private static SessionFactoryWrapper sessionFactoryWrapper;
    private static DbSetup memoryDbSetup;
    private static Ev3Communicator brickCommunicator;

    private static String buildXml;
    private static String connectionUrl;
    private static String crosscompilerBasedir;
    private static String robotResourcesDir;

    private static Ev3CompilerWorkflow compilerWorkflow;

    private static ClientUser restUser;
    private static ClientProgram restProgram;

    private static Response response;
    private static HttpSessionState s1;

    private static String blocklyProgram;
    private static Session nativeSession;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        RoundTripTest.initialize();
        RoundTripTest.driver = SeleniumHelper.runBrowser(RoundTripTest.browserVisibility);
        RoundTripTest.setUpDatabase();
        RoundTripTest.startServerAndLogin();
    }

    @Test
    public void actionMove() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[0]);
    }

    @Test
    public void actionDrive() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[1]);
    }

    @Test
    public void actionShow() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[2]);
    }

    @Test
    public void actionSound() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[3]);
    }

    @Test
    public void actionStatusLight() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[4]);
    }

    @Test
    public void actionSensors() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[5]);
    }

    @Test
    public void actionLogic() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[6]);
    }

    @Test
    public void actionControl() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[7]);
    }

    @Test
    public void actionWait() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[8]);
    }

    @Test
    public void actionMath() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[9]);
    }

    @Test
    public void actionText() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[10]);
    }

    @Test
    public void actionLists() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[11]);
    }

    @Test
    public void actionMethods() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[12]);
    }

    @Test
    public void actionBluetooth() throws Exception {
        assertRoundTrip(RoundTripTest.blocklyPrograms[13]);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        RoundTripTest.driver.quit();
        String verificationErrorString = RoundTripTest.verificationErrors.toString();
        if ( !"".equals(verificationErrorString) ) {
            Assert.fail(verificationErrorString);
        }
        RoundTripTest.server.stop();
    }

    private static void initialize() {
        Properties properties = Util.loadProperties("classpath:openRoberta.properties");
        RoundTripTest.buildXml = properties.getProperty("crosscompiler.build.xml");
        RoundTripTest.connectionUrl = properties.getProperty("hibernate.connection.url");
        RoundTripTest.crosscompilerBasedir = properties.getProperty("crosscompiler.basedir");
        RoundTripTest.robotResourcesDir = properties.getProperty("robot.resources.dir");
        RoundTripTest.browserVisibility = Boolean.parseBoolean(properties.getProperty("browser.visibility"));

        RoundTripTest.sessionFactoryWrapper = new SessionFactoryWrapper("hibernate-cfg.xml", RoundTripTest.connectionUrl);
        RoundTripTest.nativeSession = RoundTripTest.sessionFactoryWrapper.getNativeSession();
        RoundTripTest.memoryDbSetup = new DbSetup(RoundTripTest.nativeSession);
        RoundTripTest.memoryDbSetup.runDefaultRobertaSetup();
        RoundTripTest.brickCommunicator = new Ev3Communicator();
        RoundTripTest.compilerWorkflow =
            new Ev3CompilerWorkflow(
                RoundTripTest.brickCommunicator,
                RoundTripTest.crosscompilerBasedir,
                RoundTripTest.robotResourcesDir,
                RoundTripTest.buildXml);
        RoundTripTest.restUser = new ClientUser(RoundTripTest.brickCommunicator);
        RoundTripTest.restProgram = new ClientProgram(RoundTripTest.sessionFactoryWrapper, RoundTripTest.brickCommunicator, RoundTripTest.compilerWorkflow);

        RoundTripTest.s1 = HttpSessionState.init();
    }

    private static void setUpDatabase() throws Exception {
        Assert.assertEquals(1, RoundTripTest.getOneBigInteger("select count(*) from USER"));
        RoundTripTest.response =
            RoundTripTest.restUser.command(
                RoundTripTest.s1,
                RoundTripTest.sessionFactoryWrapper.getSession(),
                JSONUtilForServer.mkD("{'cmd':'createUser';'accountName':'orA';'password':'Pid';'userEmail':'cavy@home';'role':'STUDENT'}"));
        Assert.assertEquals(2, RoundTripTest.getOneBigInteger("select count(*) from USER"));
        Assert.assertTrue(!RoundTripTest.s1.isUserLoggedIn());
        RoundTripTest.response = //
            RoundTripTest.restUser.command( //
                RoundTripTest.s1,
                RoundTripTest.sessionFactoryWrapper.getSession(),
                JSONUtilForServer.mkD("{'cmd':'login';'accountName':'orA';'password':'Pid'}"));
        JSONUtilForServer.assertEntityRc(RoundTripTest.response, "ok", null);
        Assert.assertTrue(RoundTripTest.s1.isUserLoggedIn());
        int s1Id = RoundTripTest.s1.getUserId();
        Assert.assertEquals(0, RoundTripTest.getOneBigInteger("select count(*) from PROGRAM where OWNER_ID = " + s1Id));
        for ( String program : RoundTripTest.blocklyPrograms ) {
            RoundTripTest.blocklyProgram =
                Resources.toString(BasicPerformanceUserInteractionTest.class.getResource(RoundTripTest.resourcePath + program + ".xml"), Charsets.UTF_8);
            JSONObject fullRequest = new JSONObject("{\"log\":[];\"data\":{\"cmd\":\"saveAsP\";\"name\":\"" + program + "\";\"timestamp\":0}}");
            fullRequest.getJSONObject("data").put("program", RoundTripTest.blocklyProgram);
            RoundTripTest.response = RoundTripTest.restProgram.command(RoundTripTest.s1, fullRequest);
            JSONUtilForServer.assertEntityRc(RoundTripTest.response, "ok", null);
        }
    }

    private static void startServerAndLogin() throws IOException, InterruptedException {
        RoundTripTest.server = new ServerStarter("classpath:openRoberta.properties").start("localhost", null);
        int port = RoundTripTest.server.getURI().getPort();
        RoundTripTest.baseUrl = "http://localhost:" + port;
        RoundTripTest.driver.get(RoundTripTest.baseUrl + "/");
        RoundTripTest.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //Welcome message dismiss
        (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("startEV3"))).click();

        //Login
        WebElement user = (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("head-navi-icon-user")));

        while ( !RoundTripTest.driver.findElement(By.id("menuLogin")).isDisplayed() ) {
            user.click();
        }

        WebElement userLoginElement = (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("menuLogin")));
        userLoginElement.click();
        (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("doLogin")));
        RoundTripTest.driver.findElement(By.id("accountNameS")).clear();
        RoundTripTest.driver.findElement(By.id("accountNameS")).sendKeys("orA");
        RoundTripTest.driver.findElement(By.id("pass1S")).clear();
        RoundTripTest.driver.findElement(By.id("pass1S")).sendKeys("Pid");
        RoundTripTest.driver.findElement(By.id("doLogin")).click();
        (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("doLogin")));
    }

    private String saveProgram(String programName) throws InterruptedException, Exception, JSONException {

        WebElement programElement = RoundTripTest.driver.findElement(By.id("head-navi-icon-program"));

        while ( !RoundTripTest.driver.findElement(By.id("menuSaveProg")).isDisplayed() ) {
            programElement.click();
        }
        WebElement userProgramSaveAsElement =
            (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("menuSaveProg")));
        userProgramSaveAsElement.click();

        RoundTripTest.response =
            RoundTripTest.restProgram.command(RoundTripTest.s1, JSONUtilForServer.mkD("{'cmd':'loadP';'name':'" + programName + "';'owner':'orA'}"));
        String resultProgram = ((JSONObject) RoundTripTest.response.getEntity()).getString("data");
        return resultProgram;
    }

    private void loadProgram(String program) throws InterruptedException {
        WebElement programElement = RoundTripTest.driver.findElement(By.id("head-navi-icon-program"));

        while ( !RoundTripTest.driver.findElement(By.id("menuListProg")).isDisplayed() ) {
            programElement.click();
        }

        WebElement userProgramOpenElement = (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("menuListProg")));
        userProgramOpenElement.click();
        WebElement programTable = (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("programNameTable")));

        (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.textToBePresentInElement(programTable, program));

        WebElement td = findTableRow(program, programTable);
        td.click();

        WebElement load = (new WebDriverWait(RoundTripTest.driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("loadFromListing")));
        load.click();
    }

    protected WebElement findTableRow(String program, WebElement programTable) {
        Iterator<WebElement> it = programTable.findElements(By.tagName("tr")).iterator();
        it.next();
        while ( it.hasNext() ) {
            WebElement webE = it.next();
            WebElement tt = webE.findElement(By.tagName("td"));
            if ( tt.getText().equals(program) ) {
                return webE.findElement(By.tagName("td"));
            }
        }
        return null;
    }

    protected void assertRoundTrip(String programName) throws InterruptedException, Exception, JSONException, IOException {
        loadProgram(programName);

        String resultProgram = saveProgram(programName);
        RoundTripTest.blocklyProgram =
            Resources.toString(BasicPerformanceUserInteractionTest.class.getResource(RoundTripTest.resourcePath + programName + ".xml"), Charsets.UTF_8);
        Helper.assertXML(RoundTripTest.blocklyProgram, resultProgram);
    }

    private static long getOneBigInteger(String sqlStmt) {
        return RoundTripTest.memoryDbSetup.getOneBigIntegerAsLong(sqlStmt);
    }

}
