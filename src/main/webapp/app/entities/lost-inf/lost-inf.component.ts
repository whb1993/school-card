import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILostInf } from 'app/shared/model/lost-inf.model';
import { AccountService } from 'app/core';
import { LostInfService } from './lost-inf.service';

@Component({
    selector: 'jhi-lost-inf',
    templateUrl: './lost-inf.component.html'
})
export class LostInfComponent implements OnInit, OnDestroy {
    lostInfs: ILostInf[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lostInfService: LostInfService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lostInfService
            .query()
            .pipe(
                filter((res: HttpResponse<ILostInf[]>) => res.ok),
                map((res: HttpResponse<ILostInf[]>) => res.body)
            )
            .subscribe(
                (res: ILostInf[]) => {
                    this.lostInfs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLostInfs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILostInf) {
        return item.id;
    }

    registerChangeInLostInfs() {
        this.eventSubscriber = this.eventManager.subscribe('lostInfListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
