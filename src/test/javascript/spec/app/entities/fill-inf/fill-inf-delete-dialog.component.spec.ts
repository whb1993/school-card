/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SchoolCardTestModule } from '../../../test.module';
import { FillInfDeleteDialogComponent } from 'app/entities/fill-inf/fill-inf-delete-dialog.component';
import { FillInfService } from 'app/entities/fill-inf/fill-inf.service';

describe('Component Tests', () => {
    describe('FillInf Management Delete Component', () => {
        let comp: FillInfDeleteDialogComponent;
        let fixture: ComponentFixture<FillInfDeleteDialogComponent>;
        let service: FillInfService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [FillInfDeleteDialogComponent]
            })
                .overrideTemplate(FillInfDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FillInfDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FillInfService);
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
