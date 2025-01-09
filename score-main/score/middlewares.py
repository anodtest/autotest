# Define here the models for your spider middleware
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals

# useful for handling different item types with a single interface
from itemadapter import is_item, ItemAdapter


class ScoreSpiderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_spider_input(self, response, spider):
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, response, result, spider):
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, or item objects.
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Request or item objects.
        pass

    def process_start_requests(self, start_requests, spider):
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        for r in start_requests:
            yield r

    def spider_opened(self, spider):
        spider.logger.info("Spider opened: %s" % spider.name)


class ScoreDownloaderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        return None

    def process_response(self, request, response, spider):
        # Called with the response returned from the downloader.

        # Must either;
        # - return a Response object
        # - return a Request object
        # - or raise IgnoreRequest
        return response

    def process_exception(self, request, exception, spider):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        pass

    def spider_opened(self, spider):
        spider.logger.info("Spider opened: %s" % spider.name)

import time
from scrapy.http import HtmlResponse
from scrapy.utils.python import to_bytes
from selenium import webdriver
from scrapy import signals
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
class SeleniumMiddleware(object):

    @classmethod
    def from_crawler(cls, crawler):
        middleware = cls()
        crawler.signals.connect(middleware.spider_opened, signals.spider_opened)
        crawler.signals.connect(middleware.spider_closed, signals.spider_closed)
        return middleware

    def is_element_visible(self, xpath):
        item_views = self.driver.find_elements(By.XPATH, xpath)
        print("LEN ITEM VIEWS: " + str(len(item_views)))
        if len(item_views) == 15:
            return True
        else: 
            return False

    def process_request(self, request, spider):
        request.meta['driver'] = self.driver  # to access driver from response
        html_arr =[] 
        f = open("html_resp.html", "w")
        self.driver.get(request.url)
        # finished_btn = WebDriverWait(self.driver, 10).until(EC.visibility_of_element_located((By.XPATH, '//span[text()="Finished"]')) )
        # finished_btn.click()


        WebDriverWait(self.driver, 10).until( EC.visibility_of_element_located((By.CLASS_NAME, "match-container")))
        current_scroll_position, new_height= 0, 1
        while current_scroll_position <= new_height:
            item_wrapper = self.driver.find_element(By.CLASS_NAME, "vue-recycle-scroller__item-wrapper")
            inner_html = item_wrapper.get_attribute('innerHTML')
            # FIXME: Use driver to find element instead of string.find
            if inner_html.find("status-tip-page") >= 0:
                print("FOUND STATUS\n")
                break

            length = len(html_arr)
            if length > 0:
                if html_arr[length - 1] != inner_html:
                    html_arr.append(inner_html) 
            else:
                html_arr.append(inner_html)

            current_scroll_position += 8
            self.driver.execute_script("window.scrollTo(0, {});".format(current_scroll_position))
            new_height = self.driver.execute_script("return document.body.scrollHeight")
        

        html_resp = ''.join(html_arr)
        f.write(html_resp)
        body = to_bytes(html_resp)  # body must be of type bytes 
        f.close()

        return HtmlResponse(self.driver.current_url, body=body, encoding='utf-8', request=request)

    def spider_opened(self, spider):
        self.driver = webdriver.Firefox()

    def spider_closed(self, spider):
        pass
        # self.driver.close()