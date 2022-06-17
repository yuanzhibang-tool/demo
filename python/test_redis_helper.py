import unittest
import os
from redis_helper import RedisHelper

app_id = os.getenv('TEST_APP_ID')
redis_config = {
    "host": "localhost",
    "port": 6379
}


class TestRedisHelper(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_get_server_access_token_cache_key(self):
        key = RedisHelper.get_server_access_token_cache_key(app_id)
        expect_key = "type/server_access_token_info/app_id/%s" % app_id
        self.assertEqual(key, expect_key)

    def test_get_js_ticket_cache_key(self):
        key = RedisHelper.get_js_ticket_cache_key(app_id)
        expect_key = "type/js_ticket_info/app_id/%s" % app_id
        self.assertEqual(key, expect_key)

    def test_get_server_access_token(self):
        helper = RedisHelper(redis_config)
        value = helper.get_server_access_token(app_id)
        expect_value = ""
        self.assertEqual(value, expect_value)
