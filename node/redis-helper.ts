import { RedisClientType } from '@redis/client';
import { createClient } from 'redis';

export class RedisHelper {
    client: RedisClientType;
    constructor(redisUrl: string) {
        this.client = createClient({ url: redisUrl });
    }
    getJsTicket(appId: string) {
        const key = `type/js_ticket_info/app_id/${appId}`;
        this.client.get(key).then((value) => {
            console.log(value);
        }).catch((error) => {
            console.log(error);
        });
    }
    getServerAccessToken() { }

    getRedisJsonValueByKey(key: string, codeKey: string) {
        this.client.get(key).then((value) => {
            console.log(value);
        }).catch((error) => {
            console.log(error);
        });
    }
}

const helper = new RedisHelper('redis://default:p8WOmXgzZg@demo-dev-cache-redis:6379');
helper.getJsTicket("100027");