import requests


class OauthError(Exception):

    def __init__(self, code, message, response):
        self.code = code
        self.message = message
        self.response = response


class OauthNetworkError(OauthError):
    pass


class OauthApiError(OauthError):
    pass


class ApiRequestHelper:
    @staticmethod
    def post(url, data, proxies=None):
        response = requests.post(url=url, data=data, proxies=proxies)
        ApiRequestHelper.raise_network_error(response)
        ApiRequestHelper.raise_api_error(response)
        response_object = response.json()
        return response_object['data']

    @staticmethod
    def raise_network_error(response):
        status_code = response.status_code
        if status_code != 200:
            raise OauthNetworkError(status_code, 'network error', response)

    @ staticmethod
    def raise_api_error(response):
        try:
            response_object = response.json()
            status: str = response_object['status']
            message = response_object['message']
        except Exception as e:
            raise OauthNetworkError("0000", 'network error', response)
        if status.startswith('2') is False:
            raise OauthApiError(status, message, response)

    @ staticmethod
    def test():
        ApiRequestHelper.post('https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp', {},
                              {
            "http": '123:12345678@demo-proxy:7789',
            "https": '123:12345678@demo-proxy:7789'
        })
