import { Moment } from 'moment';

export interface IFillInf {
    id?: number;
    cardno?: string;
    cardstyle?: string;
    fillmoney?: string;
    filltime?: Moment;
    adId?: string;
    fillnum?: string;
}

export class FillInf implements IFillInf {
    constructor(
        public id?: number,
        public cardno?: string,
        public cardstyle?: string,
        public fillmoney?: string,
        public filltime?: Moment,
        public adId?: string,
        public fillnum?: string
    ) {}
}
