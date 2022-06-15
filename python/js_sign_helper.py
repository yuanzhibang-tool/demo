import uuid
from urllib.parse import urlparse
import time
import hashlib


class JsSignHelper:
    @staticmethod
    def get_guid():
        uuidString = str(uuid.uuid1())
        return uuidString

    @staticmethod
    def get_pure_url(url: str):
        urlInfo = urlparse(url)
        scheme = urlInfo.scheme
        host = urlInfo.netloc
        path = urlInfo.path
        url = f"%s://%s%s" % (scheme, host, path)
        if url.endswith("/"):
            url = url[:-1]
        return url

    @staticmethod
    def get_js_ticket_from_redis(appId):
        pass

    @staticmethod
    def get_js_sign_info(appId: str, url: str):
        jsTicket = ""  # !从redis中取出
        # 取出url中的path之前的部分,参数和锚点不参与计算
        url = JsSignHelper.get_pure_url(url)
        # 获取当前时间戳
        timestamp = int(time.time())
        # 生成nonce
        noncestr = JsSignHelper.get_guid()
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

    @staticmethod
    def test():
        JsSignHelper.get_pure_url("https://yuanzhibang.com:80/a/b/")
        JsSignHelper.get_js_sign_info("100027", "https://yuanzhibang.com/a/b")
