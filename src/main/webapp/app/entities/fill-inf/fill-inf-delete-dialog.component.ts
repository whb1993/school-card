import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFillInf } from 'app/shared/model/fill-inf.model';
import { FillInfService } from './fill-inf.service';

@Component({
    selector: 'jhi-fill-inf-delete-dialog',
    templateUrl: './fill-inf-delete-dialog.component.html'
})
export class FillInfDeleteDialogComponent {
    fillInf: IFillInf;

    constructor(protected fillInfService: FillInfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fillInfService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fillInfListModification',
                content: 'Deleted an fillInf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fill-inf-delete-popup',
    template: ''
})
export class FillInfDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fillInf }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FillInfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fillInf = fillInf;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fill-inf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fill-inf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
