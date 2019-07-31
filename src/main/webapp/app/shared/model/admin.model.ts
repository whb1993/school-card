export interface IAdmin {
    id?: number;
    adid?: string;
    adname?: string;
    adsex?: string;
}

export class Admin implements IAdmin {
    constructor(public id?: number, public adid?: string, public adname?: string, public adsex?: string) {}
}
