import unittest
import os
from redis_helper import RedisHelper
import redis
app_id = os.getenv('TEST_APP_ID')
redis_config = {
    "host": "demo-dev-cache-redis",
    "port": 6379,
    "auth": 'p8WOmXgzZg',
    "username": "default"
}

# !此单元测试需要有docker环境


class TestRedisHelper(unittest.TestCase):
    def setUp(self):
        # ! set init code info to redis
        # os.system('docker stop test-redis-server ; docker rm test-redis-server  ; docker run  -p 6379:6379 -d --name test-redis-server redis')
        client = redis.Redis(
            host=redis_config['host'], port=redis_config['port'], username=redis_config['username'], password=redis_config['auth'], decode_responses=True)
        key = "type/server_access_token_info/app_id/%s" % app_id
        value = '{"server_access_token": "server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c", "expires_in": 1654575742}'
        client.set(key, value)
        key = "type/js_ticket_info/app_id/%s" % app_id
        value = '{"js_ticket": "js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c", "expires_in": 1654575742}'
        client.set(key, value)

    def tearDown(self):
        # os.system("docker stop test-redis-server ; docker rm test-redis-server;")
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
        expect_value = "server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c"
        self.assertEqual(value, expect_value)

    def test_get_js_ticket(self):
        helper = RedisHelper(redis_config)
        value = helper.get_js_ticket(app_id)
        expect_value = "js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c"
        self.assertEqual(value, expect_value)
