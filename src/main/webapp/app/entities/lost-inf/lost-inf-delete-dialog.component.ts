import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILostInf } from 'app/shared/model/lost-inf.model';
import { LostInfService } from './lost-inf.service';

@Component({
    selector: 'jhi-lost-inf-delete-dialog',
    templateUrl: './lost-inf-delete-dialog.component.html'
})
export class LostInfDeleteDialogComponent {
    lostInf: ILostInf;

    constructor(protected lostInfService: LostInfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lostInfService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lostInfListModification',
                content: 'Deleted an lostInf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lost-inf-delete-popup',
    template: ''
})
export class LostInfDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lostInf }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LostInfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lostInf = lostInf;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lost-inf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lost-inf', { outlets: { popup: null } }]);
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
