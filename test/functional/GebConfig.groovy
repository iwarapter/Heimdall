/*
 This is the Geb configuration file.
 
 See: http://www.gebish.org/manual/current/configuration.html
*/

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver

driver = { new ChromeDriver() }

private void downloadDriver(File file, String path) {
    if (!file.exists()) {
        def ant = new AntBuilder()
        ant.get(src: path, dest: 'driver.zip')
        ant.unzip(src: 'driver.zip', dest: file.parent)
        ant.delete(file: 'driver.zip')
        ant.chmod(file: file, perm: '700')
    }
}

environments {

    // run as “grails -Dgeb.env=chrome test-app”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome_mac {
        def chromeDriver = new File('test/drivers/chrome/chromedriver')
        downloadDriver(chromeDriver, "http://chromedriver.googlecode.com/files/chromedriver_mac_23.0.1240.0.zip")
        System.setProperty('webdriver.chrome.driver', chromeDriver.absolutePath)
        driver = { new ChromeDriver() }
    }
	
	chrome_linux32 {
		def chromeDriver = new File('test/drivers/chrome/chromedriver')
		downloadDriver(chromeDriver, "https://chromedriver.storage.googleapis.com/2.10/chromedriver_linux32.zip")
		System.setProperty('webdriver.chrome.driver', chromeDriver.absolutePath)
		driver = { new ChromeDriver() }
	}
	
	chrome_linux64 {
		def chromeDriver = new File('test/drivers/chrome/chromedriver')
		downloadDriver(chromeDriver, "https://chromedriver.storage.googleapis.com/2.10/chromedriver_linux64.zip")
		System.setProperty('webdriver.chrome.driver', chromeDriver.absolutePath)
		driver = { new ChromeDriver() }
	}
	
	chrome_win32 {
		def chromeDriver = new File('test/drivers/chrome/chromedriver')
		downloadDriver(chromeDriver, "http://chromedriver.googlecode.com/files/chromedriver_mac_23.0.1240.0.zip")
		System.setProperty('webdriver.chrome.driver', chromeDriver.absolutePath)
		driver = { new ChromeDriver() }
	}
	 // run as “grails -Dgeb.env=firefox test-app”
	 // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	 firefox {
		 driver = { new FirefoxDriver() }
	 }

}