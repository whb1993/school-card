import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILibraryInf } from 'app/shared/model/library-inf.model';

@Component({
    selector: 'jhi-library-inf-detail',
    templateUrl: './library-inf-detail.component.html'
})
export class LibraryInfDetailComponent implements OnInit {
    libraryInf: ILibraryInf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ libraryInf }) => {
            this.libraryInf = libraryInf;
        });
    }

    previousState() {
        window.history.back();
    }
}
