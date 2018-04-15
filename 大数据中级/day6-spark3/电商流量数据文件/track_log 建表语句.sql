drop table track_log;
create table track_log (
id                         string ,
url                        string ,
referer                    string ,
keyword                    string ,
type                       string ,
guid                       string ,
pageId                     string ,
moduleId                   string ,
linkId                     string ,
attachedInfo               string ,
sessionId                  string ,
trackerU                   string ,
trackerType                string ,
ip                         string ,
trackerSrc                 string ,
cookie                     string ,
orderCode                  string ,
trackTime                  string ,
endUserId                  string ,
firstLink                  string ,
sessionViewNo              string ,
productId                  string ,
curMerchantId              string ,
provinceId                 string ,
cityId                     string )  
PARTITIONED BY (date string,hour string)  
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';


    
    
