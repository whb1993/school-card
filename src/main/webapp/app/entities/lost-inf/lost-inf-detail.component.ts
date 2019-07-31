import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILostInf } from 'app/shared/model/lost-inf.model';

@Component({
    selector: 'jhi-lost-inf-detail',
    templateUrl: './lost-inf-detail.component.html'
})
export class LostInfDetailComponent implements OnInit {
    lostInf: ILostInf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lostInf }) => {
            this.lostInf = lostInf;
        });
    }

    previousState() {
        window.history.back();
    }
}
