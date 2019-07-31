import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPressInf } from 'app/shared/model/press-inf.model';
import { AccountService } from 'app/core';
import { PressInfService } from './press-inf.service';

@Component({
    selector: 'jhi-press-inf',
    templateUrl: './press-inf.component.html'
})
export class PressInfComponent implements OnInit, OnDestroy {
    pressInfs: IPressInf[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected pressInfService: PressInfService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.pressInfService
            .query()
            .pipe(
                filter((res: HttpResponse<IPressInf[]>) => res.ok),
                map((res: HttpResponse<IPressInf[]>) => res.body)
            )
            .subscribe(
                (res: IPressInf[]) => {
                    this.pressInfs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPressInfs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPressInf) {
        return item.id;
    }

    registerChangeInPressInfs() {
        this.eventSubscriber = this.eventManager.subscribe('pressInfListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
