export interface IStudent {
    id?: number;
    sid?: string;
    sname?: string;
    ssex?: string;
    sspecial?: string;
    sclass?: string;
}

export class Student implements IStudent {
    constructor(
        public id?: number,
        public sid?: string,
        public sname?: string,
        public ssex?: string,
        public sspecial?: string,
        public sclass?: string
    ) {}
}
