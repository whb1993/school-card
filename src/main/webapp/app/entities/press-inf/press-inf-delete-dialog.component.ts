import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPressInf } from 'app/shared/model/press-inf.model';
import { PressInfService } from './press-inf.service';

@Component({
    selector: 'jhi-press-inf-delete-dialog',
    templateUrl: './press-inf-delete-dialog.component.html'
})
export class PressInfDeleteDialogComponent {
    pressInf: IPressInf;

    constructor(protected pressInfService: PressInfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pressInfService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pressInfListModification',
                content: 'Deleted an pressInf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-press-inf-delete-popup',
    template: ''
})
export class PressInfDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pressInf }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PressInfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pressInf = pressInf;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/press-inf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/press-inf', { outlets: { popup: null } }]);
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
