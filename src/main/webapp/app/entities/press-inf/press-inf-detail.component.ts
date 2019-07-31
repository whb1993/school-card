import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPressInf } from 'app/shared/model/press-inf.model';

@Component({
    selector: 'jhi-press-inf-detail',
    templateUrl: './press-inf-detail.component.html'
})
export class PressInfDetailComponent implements OnInit {
    pressInf: IPressInf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pressInf }) => {
            this.pressInf = pressInf;
        });
    }

    previousState() {
        window.history.back();
    }
}
