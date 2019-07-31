import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILibraryInf } from 'app/shared/model/library-inf.model';
import { LibraryInfService } from './library-inf.service';

@Component({
    selector: 'jhi-library-inf-delete-dialog',
    templateUrl: './library-inf-delete-dialog.component.html'
})
export class LibraryInfDeleteDialogComponent {
    libraryInf: ILibraryInf;

    constructor(
        protected libraryInfService: LibraryInfService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.libraryInfService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'libraryInfListModification',
                content: 'Deleted an libraryInf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-library-inf-delete-popup',
    template: ''
})
export class LibraryInfDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ libraryInf }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LibraryInfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.libraryInf = libraryInf;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/library-inf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/library-inf', { outlets: { popup: null } }]);
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
