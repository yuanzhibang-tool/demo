from api_request_helper import ApiRequestHelper, OauthApiError


class OauthApiHelper:
    @staticmethod
    def check_code(app_id, code, type, proxies=None):
        api = OauthApiHelper.get_api_by_path("/OAuth2/checkCode")
        try:
            response_data = ApiRequestHelper.post(
                api, {"app_id": app_id, "code": code, "type": type}, proxies)
            return response_data
        except OauthApiError as e:
            status_code = e.code
            if status_code == "4102":
                return False
            else:
                raise e

    @staticmethod
    def get_app_user_count(app_id, access_token, proxies=None):
        api = OauthApiHelper.get_api_by_path("/CommonResource/getUserCount")
        params = {"app_id": app_id, "access_token": access_token}
        response_data = ApiRequestHelper.post(api, params, proxies)
        count = response_data["user_count"]
        return count

    @staticmethod
    def get_app_user_list(app_id, access_token, proxies=None):
        api = OauthApiHelper.get_api_by_path("/CommonResource/getUserList")
        params = {"app_id": app_id, "access_token": access_token,
                  "load_more_id": 0, "load_more_count": 100}
        response_data = ApiRequestHelper.post(api, params, proxies)
        return response_data

    @staticmethod
    def get_user_app_access(app_id, open_id, access_token, proxies=None):
        api = OauthApiHelper.get_api_by_path("/UserResource/getAppAccess")
        params = {"app_id": app_id, "open_id": open_id,
                  "access_token": access_token}
        response_data = ApiRequestHelper.post(api, params, proxies)
        return response_data

    @staticmethod
    def get_user_is_app_added(app_id, open_id, access_token, proxies=None):
        api = OauthApiHelper.get_api_by_path("/UserResource/getAppIsAdded")
        params = {"app_id": app_id, "open_id": open_id,
                  "access_token": access_token}
        response_data = ApiRequestHelper.post(api, params, proxies)
        is_added = response_data['is_added']
        return is_added

    @staticmethod
    def get_user_base_info(app_id, open_id, access_token, proxies=None):
        api = OauthApiHelper.get_api_by_path("/UserResource/getUserBaseInfo")
        params = {"app_id": app_id, "open_id": open_id,
                  "access_token": access_token}
        try:
            response_data = ApiRequestHelper.post(api, params, proxies)
            return response_data
        except OauthApiError as e:
            status_code = e.code
            if status_code == "4103":
                # !没有权限获取，提示用户授权
                return False
            else:
                raise e

    @staticmethod
    def get_api_by_path(path: str):
        api = "https://oauth.yuanzhibang.com" + path
        return api


# OauthApiHelper.check_code("101170","d6e8d4288c3ed26d710d3a53a6e86572e7677e93618d69d495a6f38869280fb97","js_ticket")
# OauthApiHelper.get_app_user_list("101170")
# open_id = 'b3dFUWFoMW0vUFgwSGxzWlNOV3JLc2pFRENnSlp6Z2NBMFpsZ3NvQXVMVTR2RnJsUkRtQU5MS1Z3V2hSYzdtQ3hnQkZzelhjT0lXbTBGWmVOdHBRYTAwNys0NisramlxU21PZ3lrb1o5Q3FORC96bStTNW5ZbEtiRjRLeUQ5SVFsN1gyUHVld1lJaDkvWGJqZ0trNGx3eWZaUWhORDc1UjBWSGFDWVpFNlhnPQ'
# open_id = 'VS9XVWxEMkNocHRyaTQ4TDRwblpMNU1tSVdjbXUwQytkd2ZMUlBqRXdnTTVqYWluVXExdXhQbzJBUDZjdnNwTVpoRTY5SXhxd0VCZU9Jc3ZvekkrWE1McE9wNzdkVTUvWjg3c2hlQzNqQUVBM2FOOHF0Y29hanI2SElmczkxS3g0eTkvSk9OQysrcGFyV21VTzJhQm9ZSGliS2ppdnlwR0JMZUIrKzJraFIwPQ'
# user_list = OauthApiHelper.get_app_user_list("101170")
# print(user_list)
# count = OauthApiHelper.get_app_user_count("101170")
# print(count)

# user_base_info = OauthApiHelper.get_user_base_info("101170", open_id)
# print(user_base_info)
