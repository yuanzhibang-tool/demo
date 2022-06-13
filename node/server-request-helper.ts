import got from 'got';

interface ServerResponse {
    status: string;
    message: string;
    data?: any;
}

class ServerRequestHelper {
    static post(api: string, data: any) {
        return new Promise<ServerResponse>((resolve, reject) => {
            got.post('https://baidu.com', {
                form: data
            }).then((response) => {
                const stringBody = response.body;
                const responseObject = JSON.parse(stringBody);
                resolve(responseObject);
            }).catch((error) => {
                reject(error)
            });
        });
    }
}

ServerRequestHelper.post('', {});