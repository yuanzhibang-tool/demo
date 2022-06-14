import got from 'got';
import { HttpsProxyAgent } from 'hpagent';

interface ServerResponse {
    status: string;
    message: string;
    data?: any;
}

class ServerRequestHelper {
    static post(api: string, data: any) {
        const httpsAgent = new HttpsProxyAgent({
            keepAlive: true,
            keepAliveMsecs: 1000,
            maxSockets: 256,
            maxFreeSockets: 256,
            proxy: 'http://proxy_user:F2pkto4GtRPAqTpY@x.orzzzzzz.com:7789'
        })
        const httpAgent = new HttpsProxyAgent({
            keepAlive: true,
            keepAliveMsecs: 1000,
            maxSockets: 256,
            maxFreeSockets: 256,
            proxy: 'http://proxy_user:F2pkto4GtRPAqTpY@x.orzzzzzz.com:7789'
        })
        return new Promise<ServerResponse>((resolve, reject) => {
            got.post('https://api-service.david-health.cn/api/v1/Ip/getClientIp', {
                form: data,
                agent: {
                    https: httpsAgent,
                    http: httpAgent
                }
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