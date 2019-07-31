/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SchoolCardTestModule } from '../../../test.module';
import { LibraryInfDeleteDialogComponent } from 'app/entities/library-inf/library-inf-delete-dialog.component';
import { LibraryInfService } from 'app/entities/library-inf/library-inf.service';

describe('Component Tests', () => {
    describe('LibraryInf Management Delete Component', () => {
        let comp: LibraryInfDeleteDialogComponent;
        let fixture: ComponentFixture<LibraryInfDeleteDialogComponent>;
        let service: LibraryInfService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LibraryInfDeleteDialogComponent]
            })
                .overrideTemplate(LibraryInfDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LibraryInfDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryInfService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
