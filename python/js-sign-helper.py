import uuid
from urllib.parse import urlparse
import time
import hashlib


class JsSignHelper:
    @staticmethod
    def getGuid():
        uuidString = str(uuid.uuid1())
        return uuidString

    @staticmethod
    def getPureUrl(url: str):
        urlInfo = urlparse(url)
        scheme = urlInfo.scheme
        host = urlInfo.netloc
        path = urlInfo.path
        url = f"%s://%s%s" % (scheme, host, path)
        if url.endswith("/"):
            url = url[:-1]
        return url

    @staticmethod
    def getJsSignInfo(appId: str, url: str):
        jsTicket = ""
        # 取出url中的path之前的部分,参数和锚点不参与计算
        url = JsSignHelper.getPureUrl(url)
        # 获取当前时间戳
        timestamp = int(time.time())
        # 生成nonce
        noncestr = JsSignHelper.getGuid()
        # jsTicket = "a4dcdk"
        # timestamp = 1654850924
        # noncestr = '1234'
        # 组合签名字符串
        string = f"js_ticket=%s&nonce_str=%s&timestamp=%d&url=%s" % (
            jsTicket, noncestr, timestamp, url)
        hash_object = hashlib.sha1(string.encode("utf-8"))
        sign = hash_object.hexdigest()
        returnData = {}
        returnData['app_id'] = appId
        returnData['timestamp'] = f"%d" % timestamp
        returnData['nonce_str'] = noncestr
        returnData['signature'] = sign
        return returnData


# JsSignHelper.getPureUrl("https://yuanzhibang.com:80/a/b/")
# JsSignHelper.getJsSignInfo("100027", "https://yuanzhibang.com/a/b")
