import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILostInf } from 'app/shared/model/lost-inf.model';
import { LostInfService } from './lost-inf.service';

@Component({
    selector: 'jhi-lost-inf-update',
    templateUrl: './lost-inf-update.component.html'
})
export class LostInfUpdateComponent implements OnInit {
    lostInf: ILostInf;
    isSaving: boolean;
    lostime: string;

    constructor(protected lostInfService: LostInfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lostInf }) => {
            this.lostInf = lostInf;
            this.lostime = this.lostInf.lostime != null ? this.lostInf.lostime.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.lostInf.lostime = this.lostime != null ? moment(this.lostime, DATE_TIME_FORMAT) : null;
        if (this.lostInf.id !== undefined) {
            this.subscribeToSaveResponse(this.lostInfService.update(this.lostInf));
        } else {
            this.subscribeToSaveResponse(this.lostInfService.create(this.lostInf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILostInf>>) {
        result.subscribe((res: HttpResponse<ILostInf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
