import time
import unittest
from js_sign_helper import JsSignHelper
import os

app_id = os.getenv('TEST_APP_ID')
url = "https://yuanzhibang.com/a/b/?x=1&v=x#12"


class TestJsSignHelper(unittest.TestCase):

    def test_get_guid(self):
        guid_1 = JsSignHelper.get_guid()
        guid_2 = JsSignHelper.get_guid()
        self.assertNotEqual(guid_1, guid_2)
        self.assertIsInstance(guid_1, str)
        self.assertIsInstance(guid_2, str)

    def test_get_pure_url(self):
        pure_url = JsSignHelper.get_pure_url(url)
        self.assertEqual(pure_url, "https://yuanzhibang.com/a/b")

    def test_get_sign(self):
        sign = JsSignHelper.get_sign(
            "a4dcdk", "1234", 1654850924, "https://yuanzhibang.com/a/b/?x=1&v=x#12")
        self.assertEqual(sign, "a8cb02e00c2759372954bf5516d110066b911aa4")

    def test_get_js_sign_info(self):
        test_js_tickert = "123"
        js_sign_info = JsSignHelper.get_js_sign_info(
            app_id, url, test_js_tickert)
        js_sign_info_subset = {
            "app_id": app_id,
            "url": url
        }
        self.assertDictContainsSubset(js_sign_info_subset, js_sign_info)
        time_diff = int(time.time()) - int(js_sign_info['timestamp'])
        self.assertLessEqual(time_diff, 10)
        expect_sign = JsSignHelper.get_sign(
            test_js_tickert, js_sign_info['nonce_str'], int(js_sign_info['timestamp']), url)
        self.assertEqual(js_sign_info['signature'], expect_sign)
