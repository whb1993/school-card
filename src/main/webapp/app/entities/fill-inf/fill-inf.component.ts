import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFillInf } from 'app/shared/model/fill-inf.model';
import { AccountService } from 'app/core';
import { FillInfService } from './fill-inf.service';

@Component({
    selector: 'jhi-fill-inf',
    templateUrl: './fill-inf.component.html'
})
export class FillInfComponent implements OnInit, OnDestroy {
    fillInfs: IFillInf[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected fillInfService: FillInfService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.fillInfService
            .query()
            .pipe(
                filter((res: HttpResponse<IFillInf[]>) => res.ok),
                map((res: HttpResponse<IFillInf[]>) => res.body)
            )
            .subscribe(
                (res: IFillInf[]) => {
                    this.fillInfs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFillInfs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFillInf) {
        return item.id;
    }

    registerChangeInFillInfs() {
        this.eventSubscriber = this.eventManager.subscribe('fillInfListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
