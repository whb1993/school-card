import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILibraryInf } from 'app/shared/model/library-inf.model';
import { LibraryInfService } from './library-inf.service';

@Component({
    selector: 'jhi-library-inf-update',
    templateUrl: './library-inf-update.component.html'
})
export class LibraryInfUpdateComponent implements OnInit {
    libraryInf: ILibraryInf;
    isSaving: boolean;

    constructor(protected libraryInfService: LibraryInfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ libraryInf }) => {
            this.libraryInf = libraryInf;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.libraryInf.id !== undefined) {
            this.subscribeToSaveResponse(this.libraryInfService.update(this.libraryInf));
        } else {
            this.subscribeToSaveResponse(this.libraryInfService.create(this.libraryInf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibraryInf>>) {
        result.subscribe((res: HttpResponse<ILibraryInf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
