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


class ServerRequestHelper:
    @staticmethod
    def post(url, data, proxies=None):
        response = requests.post(url=url, data=data, proxies=proxies)
        ServerRequestHelper.raise_network_error(response)
        ServerRequestHelper.raise_api_error(response)
        response_object = response.json()
        return response_object['data']

    @staticmethod
    def raise_network_error(response):
        status_code = response.status_code
        if status_code != 200:
            raise OauthNetworkError(status_code, '', response)

    @staticmethod
    def raise_api_error(response):
        response_object = response.json()
        status: str = response_object['status']
        message = response_object['message']
        if status.startswith('2') is False:
            raise OauthApiError(status, message, response)


ServerRequestHelper.post('https://baidu.com', {})
