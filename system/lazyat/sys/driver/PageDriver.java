package lazyat.sys.driver;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PageDriver {

	/**
	 * PageDriver: 构建方法
	 */
	public PageDriver() {
		driver = getLocalDriver("Firefox");
	}

	/**
	 * PageDriver: 构建方法
	 */
	public PageDriver(String browserName) {
		driver = getLocalDriver(browserName);
	}

	/**
	 * PageDriver: 构建方法
	 * @param browserName 浏览器名
	 * @param browserVersion 浏览器版本号
	 * @param browserPlatform 系统平台
	 * @param serverUrl 服务端地址
	 */
	public PageDriver(String browserName, String browserVersion, String browserPlatform, String serverUrl) {
		driver = getRemoteDriver(serverUrl, getCapability(browserName, browserVersion, browserPlatform));
	}

	/**
	 * driver: 测试驱动
	 */
	private WebDriver driver = null;

	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * getLocalDriver: 获取本地测试驱动
	 * @param browserName 浏览器名
	 * @return driver 测试驱动
	 */
	private WebDriver getLocalDriver(String browserName) {
		try {
			switch (browserName.toLowerCase()) {
			case "firefox":
				return new FirefoxDriver();
			case "chrome":
				return new ChromeDriver();
			case "opera":
				return new OperaDriver();
			case "safari":
				return new SafariDriver();
			case "ie":
				return new InternetExplorerDriver();
			case "edge":
				return new EdgeDriver();
			default:
				return new FirefoxDriver();
			}
		} catch (Exception e) { // 驱动加载错误
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * getRemoteDriver: 获取远程测试驱动
	 * @param serverUrl 服务端地址
	 * @param capability 浏览器设定
	 * @return driver 测试驱动
	 */
	private WebDriver getRemoteDriver(String serverUrl, DesiredCapabilities capability) {
		try {
			URL url = new URL("http://" + serverUrl + "/wd/hub");
			return new RemoteWebDriver(url, capability);
		} catch (Exception e) { // 驱动加载错误
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * getCapability: 获取浏览器设定
	 * @param browserName 浏览器名
	 * @param browserVersion 浏览器版本号
	 * @param browserPlatform 系统平台
	 * @return capability 浏览器设定
	 */
	private DesiredCapabilities getCapability(String browserName, String browserVersion, String browserPlatform) {
		DesiredCapabilities capability = null;
		switch (browserName.toLowerCase()) {
		case "firefox":
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			break;
		case "chrome":
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			break;
		case "opera":
			capability = DesiredCapabilities.operaBlink();
			capability.setBrowserName("opera");
			break;
		case "safari":
			capability = DesiredCapabilities.safari();
			capability.setBrowserName("safari");
			break;
		case "ie":
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			break;
		case "edge":
			capability = DesiredCapabilities.edge();
			capability.setBrowserName("edge");
			break;
		default:
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
		}
		switch (browserPlatform.toLowerCase()) {
		case "windows":
			capability.setPlatform(Platform.WINDOWS);
			break;
		case "linux":
			capability.setPlatform(Platform.LINUX);
			break;
		case "mac":
			capability.setPlatform(Platform.MAC);
			break;
		default:
			capability.setPlatform(Platform.WINDOWS);
		}
		capability.setVersion(browserVersion);
		return capability;
	}
}
