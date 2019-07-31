import { Moment } from 'moment';

export interface IPressInf {
    id?: number;
    pressId?: string;
    pplace?: string;
    cardId?: string;
    pmoney?: string;
    ptime?: Moment;
}

export class PressInf implements IPressInf {
    constructor(
        public id?: number,
        public pressId?: string,
        public pplace?: string,
        public cardId?: string,
        public pmoney?: string,
        public ptime?: Moment
    ) {}
}
