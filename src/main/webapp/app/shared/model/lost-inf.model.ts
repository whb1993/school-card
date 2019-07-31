import { Moment } from 'moment';

export interface ILostInf {
    id?: number;
    lostId?: string;
    cardId?: string;
    lostime?: Moment;
    adId?: string;
}

export class LostInf implements ILostInf {
    constructor(public id?: number, public lostId?: string, public cardId?: string, public lostime?: Moment, public adId?: string) {}
}
