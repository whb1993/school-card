export interface ILibraryInf {
    id?: number;
    bookId?: string;
    bookno?: string;
    cardstates?: string;
    borlist?: string;
    adid?: string;
    libId?: string;
}

export class LibraryInf implements ILibraryInf {
    constructor(
        public id?: number,
        public bookId?: string,
        public bookno?: string,
        public cardstates?: string,
        public borlist?: string,
        public adid?: string,
        public libId?: string
    ) {}
}
