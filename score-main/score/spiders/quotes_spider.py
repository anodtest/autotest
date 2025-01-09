from pathlib import Path
from selenium import webdriver
import scrapy


class QuotesSpider(scrapy.Spider):
    name = "quotes"
    start_urls = ["https://www.aiscore.com/en"]


    def __init__(self):
        self.driver = webdriver.Firefox()
    # def start_requests(self):
    #     headers = {        
    #         'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8',        
    #         'Accept-Encoding': 'gzip, deflate, br',        
    #         'Accept-Language': 'en-US,en;q=0.5',        
    #         'Connection': 'keep-alive',        
    #         'Cookie': 'AMCV_0D15148954E6C5100A4C98BC%40AdobeOrg=1176715910%7CMCIDTS%7C19271%7CMCMID%7C80534695734291136713728777212980602826%7CMCAAMLH-1665548058%7C7%7CMCAAMB-1665548058%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1664950458s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-19272%7CvVersion%7C5.4.0; s_ecid=MCMID%7C80534695734291136713728777212980602826; __cfruid=37ff2049fc4dcffaab8d008026b166001c67dd49-1664418998; AMCVS_0D15148954E6C5100A4C98BC%40AdobeOrg=1; s_cc=true; __cf_bm=NIDFoL5PTkinis50ohQiCs4q7U4SZJ8oTaTW4kHT0SE-1664943258-0-AVwtneMLLP997IAVfltTqK949EmY349o8RJT7pYSp/oF9lChUSNLohrDRIHsiEB5TwTZ9QL7e9nAH+2vmXzhTtE=; PHPSESSID=ddf49facfda7bcb4656eea122199ea0d',                        
    #         'If-Modified-Since': 'Tue, 04 May 2021 05:09:49 GMT',        
    #         'If-None-Match': 'W/"12c6a-5c17a16600f6c-gzip"',        
    #         'Sec-Fetch-Dest': 'document',        
    #         'Sec-Fetch-Mode': 'navigate',        
    #         'Sec-Fetch-Site': 'none',        
    #         'Sec-Fetch-User': '?1',        
    #         'TE': 'trailers',        
    #         'Upgrade-Insecure-Requests': '1',        
    #         'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:105.0) Gecko/20100101 Firefox/105.0'        
    #     }

    def parse(self, response):

        item_views = response.xpath("//div[@class='vue-recycle-scroller__item-view']")
        print("TOTAL ITEM VIEWS: " + str(len(item_views)))
        score_arr = []
        compCont = response.xpath('//div[@class="comp-container"]')
        for comp in compCont:
            country = ""
            name = ""

            tournaments = comp.xpath('.//div[contains(@class, "title")]')

            for tour in tournaments:
                country  = tour.xpath('.//span[contains(@class,"country-name minitext")]/text()').get()
                name  = tour.xpath('.//a[contains(@class,"compe-name minitext")]/text()').get()

            compList = comp.xpath('.//a[@class="match-container"]')
            for compChild in compList:
                score = {}
                score['tournament'] = country.strip() + ' ' + name.strip()


                homeTeam = compChild.xpath('.//span[@itemprop="homeTeam"]/text()').get()
                if isinstance(homeTeam, str):
                    homeTeam = homeTeam.strip()
                score['homeTeam'] = homeTeam
                awayTeam = compChild.xpath('.//span[@itemprop="awayTeam"]/text()').get()
                if isinstance(awayTeam, str):
                    awayTeam= awayTeam.strip()
                score['awayTeam'] = awayTeam

                homeScore = compChild.xpath('.//span[contains(@class, "score-home")]/text()').get()
                if isinstance(homeScore, str):
                    homeScore = homeScore.strip()
                    if homeScore.isdigit():
                        homeScore = int(homeScore)
                score['homeScore'] = homeScore

                awayScore = compChild.xpath('.//span[contains(@class, "score-away")]/text()').get()
                if isinstance(awayScore, str):
                    awayScore = awayScore.strip()
                    if awayScore.isdigit():
                        awayScore = int(awayScore)

                score['awayScore'] = awayScore
            
            
                # filter
                is_dup = False
                for value in score_arr:
                    if value == score:
                        is_dup = True
                        break

                if is_dup == False:
                    score_arr.append(score)


        print("TOTAL SCORE: " + str(len(score_arr)))
        import json
        with open('score.json', 'w', encoding='utf-8') as f:
            json.dump(score_arr, f, ensure_ascii=False, indent=4)
