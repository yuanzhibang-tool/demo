import unittest
from api_request_helper import ApiRequestHelper, OauthApiError, OauthNetworkError
import os
from munch import DefaultMunch

app_id = os.getenv('TEST_APP_ID')
test_proxy = os.getenv('TEST_PROXY')
test_proxy_ip = os.getenv('TEST_PROXY_IP')


class TestApiRequestHelper(unittest.TestCase):

    def test_post_proxy(self):
        proxy = {
            "https": test_proxy,
            "http": test_proxy
        }
        url = "https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp"
        with self.assertRaises(OauthNetworkError) as cm:
            ApiRequestHelper.post(url, {}, proxy)
        the_exception = cm.exception
        self.assertEqual(the_exception.code, "0000")
        self.assertEqual(the_exception.message, 'network error')
        self.assertEqual(the_exception.response.text, test_proxy_ip)

    def test_raise_network_error(self):
        mock_response = DefaultMunch.fromDict({'status_code': 201})
        with self.assertRaises(OauthNetworkError) as cm:
            ApiRequestHelper.raise_network_error(mock_response)
        the_exception = cm.exception
        self.assertEqual(the_exception.code, 201)
        self.assertEqual(the_exception.message, 'network error')
        self.assertEqual(the_exception.response, mock_response)
        mock_response = DefaultMunch.fromDict({'status_code': 200})
        ApiRequestHelper.raise_network_error(mock_response)

    def test_raise_api_error(self):
        mock_response = DefaultMunch.fromDict({'status_code': 200})

        def json():
            return {"status": "4000", "message": "not ok", "data": []}
        mock_response.json = json
        with self.assertRaises(OauthApiError) as cm:
            ApiRequestHelper.raise_api_error(mock_response)
        the_exception = cm.exception
        self.assertEqual(the_exception.code, "4000")
        self.assertEqual(the_exception.message, 'not ok')
        self.assertEqual(the_exception.response, mock_response)

        def json():
            raise Exception()
        mock_response.json = json
        with self.assertRaises(OauthNetworkError) as cm:
            ApiRequestHelper.raise_api_error(mock_response)
        the_exception = cm.exception
        self.assertEqual(the_exception.code, "0000")
        self.assertEqual(the_exception.message, 'network error')
        self.assertEqual(the_exception.response, mock_response)

        def json():
            return {"status": "2", "message": "ok", "data": []}
        mock_response.json = json
        ApiRequestHelper.raise_api_error(mock_response)
