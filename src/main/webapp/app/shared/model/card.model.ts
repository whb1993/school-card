import { Moment } from 'moment';

export interface ICard {
    id?: number;
    cardId?: string;
    cardnum?: string;
    sname?: string;
    ssex?: string;
    cardstyle?: string;
    cardmoney?: string;
    cardstates?: string;
    cardtime?: Moment;
}

export class Card implements ICard {
    constructor(
        public id?: number,
        public cardId?: string,
        public cardnum?: string,
        public sname?: string,
        public ssex?: string,
        public cardstyle?: string,
        public cardmoney?: string,
        public cardstates?: string,
        public cardtime?: Moment
    ) {}
}
