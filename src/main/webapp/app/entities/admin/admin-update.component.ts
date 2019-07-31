import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAdmin } from 'app/shared/model/admin.model';
import { AdminService } from './admin.service';

@Component({
    selector: 'jhi-admin-update',
    templateUrl: './admin-update.component.html'
})
export class AdminUpdateComponent implements OnInit {
    admin: IAdmin;
    isSaving: boolean;

    constructor(protected adminService: AdminService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ admin }) => {
            this.admin = admin;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.admin.id !== undefined) {
            this.subscribeToSaveResponse(this.adminService.update(this.admin));
        } else {
            this.subscribeToSaveResponse(this.adminService.create(this.admin));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdmin>>) {
        result.subscribe((res: HttpResponse<IAdmin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
