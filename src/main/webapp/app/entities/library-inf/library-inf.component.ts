import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILibraryInf } from 'app/shared/model/library-inf.model';
import { AccountService } from 'app/core';
import { LibraryInfService } from './library-inf.service';

@Component({
    selector: 'jhi-library-inf',
    templateUrl: './library-inf.component.html'
})
export class LibraryInfComponent implements OnInit, OnDestroy {
    libraryInfs: ILibraryInf[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected libraryInfService: LibraryInfService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.libraryInfService
            .query()
            .pipe(
                filter((res: HttpResponse<ILibraryInf[]>) => res.ok),
                map((res: HttpResponse<ILibraryInf[]>) => res.body)
            )
            .subscribe(
                (res: ILibraryInf[]) => {
                    this.libraryInfs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLibraryInfs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILibraryInf) {
        return item.id;
    }

    registerChangeInLibraryInfs() {
        this.eventSubscriber = this.eventManager.subscribe('libraryInfListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
