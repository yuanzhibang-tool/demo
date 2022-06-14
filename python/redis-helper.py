import redis
import json


class RedisHelper:
    config: object

    def __init__(self, config):
        self.config = config

    # 从数据库获取有效的server_access_token
    def get_server_access_token(self, app_id):
        client = redis.Redis(
            host=self.config['host'], port=self.config['port'], username=self.config['username'], password=self.config['auth'], decode_responses=True)
        server_access_token_info_string = client.get(
            RedisHelper.get_app_server_access_token_cache_key(app_id))
        client.close()
        if server_access_token_info_string:
            server_access_token_info = json.loads(
                server_access_token_info_string)
            if server_access_token_info:
                return server_access_token_info['server_access_token']
        return None

    def get_js_ticket(self, app_id):
        client = redis.Redis(
            host=self.config['host'], port=self.config['port'], username=self.config['username'], password=self.config['auth'], decode_responses=True)
        js_ticket_info_string = client.get(
            RedisHelper.get_app_js_ticket_cache_key(app_id))
        client.close()
        if js_ticket_info_string:
            js_ticket_info = json.loads(js_ticket_info_string)
            if js_ticket_info:
                return js_ticket_info['js_ticket']
        return None

    @staticmethod
    def get_app_server_access_token_cache_key(app_id):
        return "type/server_access_token_info/app_id/%s" % app_id

    @staticmethod
    def get_app_js_ticket_cache_key(app_id):
        return "type/js_ticket_info/app_id/%s" % app_id


config = {
    "host": "demo-dev-cache-redis",
    "port": 6379,
    "auth": 'p8WOmXgzZg',
    "username": "default"
}
helper = RedisHelper(config)
token = helper.get_server_access_token("100027")
print(token)
ticket = helper.get_js_ticket("100027")
print(ticket)
