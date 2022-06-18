import time
import unittest
from js_sign_helper import JsSignHelper
import os

from oauth_api_helper import OauthApiHelper

app_id = os.getenv('TEST_APP_ID')
test_code = os.getenv('TEST_CODE')
open_id = os.getenv('TEST_OPEN_ID')


class TestOauthApiHelper(unittest.TestCase):

    def test_get_api_by_path(self):
        api = OauthApiHelper.get_api_by_path("/x")
        expect_api = "https://oauth.yuanzhibang.com/x"
        self.assertEqual(api, expect_api)

    def test_check_code(self):
        result = OauthApiHelper.check_code(app_id, test_code, 'access_token')
        self.assertIsInstance(result['expires_in'], int)
        result = OauthApiHelper.check_code(app_id, "123", 'access_token')
        self.assertEqual(result, False)

    def test_get_app_user_count(self):
        result = OauthApiHelper.get_app_user_count(
            app_id, test_code)
        self.assertIsInstance(result, int)

    def test_get_app_user_list(self):
        result = OauthApiHelper.get_app_user_list(
            app_id, test_code)
        self.assertIsInstance(result, list)
        self.assertIsInstance(result[0]['id'], str)
        self.assertIsInstance(result[0]['open_id'], str)

    def test_get_user_app_access(self):
        result = OauthApiHelper.get_user_app_access(
            app_id, open_id, test_code)
        self.assertIsInstance(result, list)
        self.assertIsInstance(result[0], str)

    def test_get_user_is_app_added(self):
        result = OauthApiHelper.get_user_is_app_added(
            app_id, open_id, test_code)
        self.assertIsInstance(result, bool)

    def test_get_user_base_info(self):
        result = OauthApiHelper.get_user_base_info(
            app_id, open_id, test_code)
        self.assertIsInstance(result, dict)
        self.assertIsInstance(result['nick'], str)
        self.assertIsInstance(result['avatar'], str)
        self.assertIsInstance(result['sex'], str)
