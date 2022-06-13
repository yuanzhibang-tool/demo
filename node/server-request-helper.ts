import got from 'got';

class ServerRequestHelper {
    static post(api: string, data: any) {
        got.post('https://baidu.com', {
            form: data
        }).then((response) => {
            const stringBody = response.body;
            console.log(stringBody);
        }).catch((error) => {
            console.log(error);
        });
    }
}

ServerRequestHelper.post('', {});