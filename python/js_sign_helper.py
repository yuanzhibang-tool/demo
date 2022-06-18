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
    def get_js_sign_info(app_id: str, url: str, js_ticket: str):
        # 获取当前时间戳
        timestamp = int(time.time())
        # 生成nonce
        noncestr = JsSignHelper.get_guid()
        # 组合签名字符串
        sign = JsSignHelper.get_sign(js_ticket, noncestr, timestamp, url)
        returnData = {}
        returnData['app_id'] = app_id
        returnData['timestamp'] = f"%d" % timestamp
        returnData['nonce_str'] = noncestr
        returnData['signature'] = sign
        returnData['url'] = url
        return returnData

    @staticmethod
    def get_sign(js_ticket, noncestr, timestamp, url):
        # 取出url中的path之前的部分,参数和锚点不参与计算
        url = JsSignHelper.get_pure_url(url)
        sign_string = f"js_ticket=%s&nonce_str=%s&timestamp=%d&url=%s" % (
            js_ticket, noncestr, timestamp, url)
        hash_object = hashlib.sha1(sign_string.encode("utf-8"))
        sign = hash_object.hexdigest()
        return sign
