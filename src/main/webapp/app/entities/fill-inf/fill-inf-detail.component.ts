import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFillInf } from 'app/shared/model/fill-inf.model';

@Component({
    selector: 'jhi-fill-inf-detail',
    templateUrl: './fill-inf-detail.component.html'
})
export class FillInfDetailComponent implements OnInit {
    fillInf: IFillInf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fillInf }) => {
            this.fillInf = fillInf;
        });
    }

    previousState() {
        window.history.back();
    }
}
