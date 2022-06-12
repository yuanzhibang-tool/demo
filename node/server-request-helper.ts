import got from 'got';

class ServerRequestHelper {
    static post(api: string, data: any) {
        got.post('https://baidu.com', {
            form: data
        }).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        });
    }
}

ServerRequestHelper.post('', {});