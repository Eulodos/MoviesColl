import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { NavbarComponent } from './navbar.component';


describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavbarComponent,
        MatToolbar,
        MatIcon
      ],
      imports: [
        RouterTestingModule
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain logo with a title', () => {
    const logoEl = fixture.debugElement.query(By.css(".logo"));
    const icon = logoEl.query(By.css('mat-icon')).nativeElement;
    expect(logoEl.children.length).toEqual(1);
    expect(icon.textContent).toBe('theaters');
    expect(logoEl.nativeElement.textContent).toContain(component.title);
  })

  it('should contain links with proper paths', () => {
    const links = fixture.debugElement.queryAll(By.css('a'));
    expect(links[0].nativeElement.getAttribute('href')).toBe('/');
    expect(links[1].nativeElement.getAttribute('href')).toBe('/list');
    expect(links[2].nativeElement.getAttribute('href')).toBe('/about');
    expect(links[3].nativeElement.getAttribute('href')).toBe('/temp');
  })
});
