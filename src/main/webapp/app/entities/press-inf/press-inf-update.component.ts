import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPressInf } from 'app/shared/model/press-inf.model';
import { PressInfService } from './press-inf.service';

@Component({
    selector: 'jhi-press-inf-update',
    templateUrl: './press-inf-update.component.html'
})
export class PressInfUpdateComponent implements OnInit {
    pressInf: IPressInf;
    isSaving: boolean;
    ptime: string;

    constructor(protected pressInfService: PressInfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pressInf }) => {
            this.pressInf = pressInf;
            this.ptime = this.pressInf.ptime != null ? this.pressInf.ptime.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pressInf.ptime = this.ptime != null ? moment(this.ptime, DATE_TIME_FORMAT) : null;
        if (this.pressInf.id !== undefined) {
            this.subscribeToSaveResponse(this.pressInfService.update(this.pressInf));
        } else {
            this.subscribeToSaveResponse(this.pressInfService.create(this.pressInf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPressInf>>) {
        result.subscribe((res: HttpResponse<IPressInf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
