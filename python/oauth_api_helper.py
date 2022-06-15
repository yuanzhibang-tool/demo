from http import server
from api_request_helper import ApiRequestHelper, OauthApiError, OauthNetworkError

class OauthApiHelper:
    @staticmethod
    def check_code(app_id, code,type):
        api = OauthApiHelper.get_api_by_path("/OAuth2/checkCode")
        try:
            response_data = ApiRequestHelper.post(api,{"app_id":app_id,"code":code,"type":type})
            return response_data
        except OauthApiError as e:
            status_code = e.code
            if status_code == "4102":
                return False
            else:
                raise e


    @staticmethod
    def get_app_user_count(app_id):
        api = OauthApiHelper.get_api_by_path("/CommonResource/getUserCount")
        server_access_token = OauthApiHelper.get_server_access_token(app_id)
        params = {"app_id":app_id,"access_token":server_access_token}
        response_data = ApiRequestHelper.post(api,params)
        return response_data

    @staticmethod
    def get_server_access_token(app_id):
        return "658bc3778dd7a8db5209fd16e91b3b8d8d115129d22dc4ab7f42cb4f6394a625"
    @staticmethod
    def get_api_by_path(path:str):
        api = "https://oauth.yuanzhibang.com" + path
        return api


# OauthApiHelper.check_code("101170","d6e8d4288c3ed26d710d3a53a6e86572e7677e93618d69d495a6f38869280fb97","js_ticket")
OauthApiHelper.get_app_user_count("101170")