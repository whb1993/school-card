import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IFillInf } from 'app/shared/model/fill-inf.model';
import { FillInfService } from './fill-inf.service';

@Component({
    selector: 'jhi-fill-inf-update',
    templateUrl: './fill-inf-update.component.html'
})
export class FillInfUpdateComponent implements OnInit {
    fillInf: IFillInf;
    isSaving: boolean;
    filltime: string;

    constructor(protected fillInfService: FillInfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fillInf }) => {
            this.fillInf = fillInf;
            this.filltime = this.fillInf.filltime != null ? this.fillInf.filltime.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.fillInf.filltime = this.filltime != null ? moment(this.filltime, DATE_TIME_FORMAT) : null;
        if (this.fillInf.id !== undefined) {
            this.subscribeToSaveResponse(this.fillInfService.update(this.fillInf));
        } else {
            this.subscribeToSaveResponse(this.fillInfService.create(this.fillInf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFillInf>>) {
        result.subscribe((res: HttpResponse<IFillInf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
