export interface ITeacher {
    id?: number;
    tid?: string;
    tname?: string;
    tsex?: string;
    tspecial?: string;
}

export class Teacher implements ITeacher {
    constructor(public id?: number, public tid?: string, public tname?: string, public tsex?: string, public tspecial?: string) {}
}
